using AirsofterAPI.Entities;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Text.Json.Serialization;
using System.Text.Json;
using AirsofterAPI.DTO.Games;

namespace AirsofterAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class GameController : ControllerBase
    {
        private readonly AirsofterDbContext _context;

        public GameController(AirsofterDbContext context)
        {
            _context = context;
        }



        [HttpGet("list")]
        public async Task<ActionResult<GameListResponse>> GetGames()
        {
            var games = await _context.Games
                .Include(g => g.Field)
                    .ThenInclude(f => f.Province)
                .Include(g => g.Field)
                    .ThenInclude(f => f.Country)
                .ToListAsync();

            var gameDtos = games.Select(g => new GameDto
            {
                Id = g.Id,
                FieldName = g.Field.Name,
                Location = g.Field.Province.Name,
                GameDateTime = g.GameDate,
                IsAM = g.IsAM,
                CurrentPlayers = _context.UserGames.Count(gp => gp.GameId == g.Id),
                MaxPlayers = g.MaxPlayers
            }).ToList();

            var gameListResponse = new GameListResponse
            {
                GameListDto = gameDtos
            };

            return gameListResponse;
        }


        [HttpGet("{id}")]
        public async Task<ActionResult<GameDetailResponse>> GetGame(Guid id)
        {
            var game = await _context.Games
                .Include(g => g.Field)
                    .ThenInclude(f => f.Company)
                .Include(g => g.Field)
                    .ThenInclude(f => f.Country)
                .Include(g => g.Field)
                    .ThenInclude(f => f.Province)
                .FirstOrDefaultAsync(g => g.Id == id);

            if (game == null)
            {
                return NotFound();
            }

            var userGames = await _context.UserGames
                .Where(ug => ug.GameId == id)
                .Include(ug => ug.User)
                .ToListAsync();

            var playerNames = userGames.Select(ug => ug.User?.DisplayName).ToList();

            var gameDetailDto = new GameDetailDto
            {
                Id = game.Id,
                FieldName = game.Field.Name,
                CompanyName = game.Field.Company.CompanyName,
                ProvinceName = game.Field.Province.Name,
                CountryName = game.Field.Country.Name,
                Description = game.Description,
                CurrentPlayers = playerNames.Count, 
                MaxPlayers = game.MaxPlayers,
                GameDateTime = game.GameDate,
                IsAM = game.IsAM,
                Players = playerNames
            };

            var gameDetailResponse = new GameDetailResponse
            {
                GameDetailDto = gameDetailDto
            };

            return gameDetailResponse;
        }

        [HttpPost("{id}/signup")]
        public async Task<ActionResult> SignUpForGame(Guid id, [FromBody] SignUpDto signUpDto)
        {
            if (id != signUpDto.GameId)
            {
                return BadRequest("Game ID in the URL does not match Game ID in the body");
            }

            var game = await _context.Games.FirstOrDefaultAsync(g => g.Id == id);

            if (game == null)
            {
                return NotFound("Game not found");
            }

            var existingSignUp = await _context.UserGames
                .FirstOrDefaultAsync(gp => gp.GameId == signUpDto.GameId && gp.UserId == signUpDto.UserId);

            if (existingSignUp != null)
            {
                return BadRequest("User is already signed up for a game on this date");
            }

            var isSignedUpForAnotherGameOnSameDate = await _context.UserGames
                .AnyAsync(gp => gp.UserId == signUpDto.UserId && gp.Game.GameDate.Date == game.GameDate.Date);

            if (isSignedUpForAnotherGameOnSameDate)
            {
                return BadRequest("User is already signed up for another game on the same date");
            }

            var gameParticipant = new UserGame
            {
                GameId = signUpDto.GameId,
                UserId = signUpDto.UserId
            };

            _context.UserGames.Add(gameParticipant);
            await _context.SaveChangesAsync();

            return Ok("Signed up for the game successfully");
        }

        [HttpGet("next/{id}")]
        public async Task<ActionResult<GameDto>> GetNextGameForPlayer(Guid id)
        {
            var nextGame = await _context.UserGames
                .Where(ug => ug.UserId == id) // Filtrar por el ID del jugador
                .Select(ug => ug.Game) // Seleccionar las partidas asociadas con el jugador
                .Include(g => g.Field)
                    .ThenInclude(f => f.Company)
                .Include(g => g.Field)
                    .ThenInclude(f => f.Country)
                .Include(g => g.Field)
                    .ThenInclude(f => f.Province)
                .Where(g => g.GameDate >= DateTime.Now)
                .OrderBy(g => g.GameDate)
                .FirstOrDefaultAsync();

            if (nextGame == null)
            {
                return NotFound("No upcoming games found");
            }

            var gameDto = new GameDto
            {
                Id = nextGame.Id,
                FieldName = nextGame.Field.Name,
                Location = nextGame.Field.Province.Name,
                GameDateTime = nextGame.GameDate,
                IsAM = nextGame.IsAM,
                CurrentPlayers = _context.UserGames.Count(gp => gp.GameId == nextGame.Id),
                MaxPlayers = nextGame.MaxPlayers
            };

            return gameDto;
        }


    }
}


