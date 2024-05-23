using AirsofterAPI.Entities;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Text.Json.Serialization;
using System.Text.Json;
using AirsofterAPI.DTO.Games;
using static System.Runtime.InteropServices.JavaScript.JSType;

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
        public async Task<ActionResult<List<GameDetailDto>>> GetGames()
        {
            var today = DateTime.Today;

            var games = await _context.Games
                .Include(g => g.Field)
                    .ThenInclude(f => f.Province)
                .Include(g => g.Field)
                    .ThenInclude(f => f.Country)
                .Where(g => g.GameDate >= today)
                .ToListAsync();

            var gameDtos = games.Select(g => new GameDetailDto
            {
                Id = g.Id,
                FieldName = g.Field.Name,
                ProvinceName = g.Field.Province.Name,
                CountryName = g.Field.Country.Name,
                GameDateTime = g.GameDate,
                IsAM = g.IsAM,
                CurrentPlayers = _context.UserGames.Count(gp => gp.GameId == g.Id),
                MaxPlayers = g.MaxPlayers
            })
            .OrderBy(g => g.GameDateTime.Date)
            .ThenByDescending(g => g.IsAM)
            .ToList();

            return gameDtos;
        }



        [HttpGet("{id}")]
        public async Task<ActionResult<GameDetailDto>> GetGame(Guid id)
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
            var playerIds = userGames.Select(ug => ug.UserId).ToList();

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
                Players = playerNames,
                PlayerIds = playerIds
            };

            return gameDetailDto;
        }

        [HttpPost("{userId}/signup/{gameId}")]
        public async Task<ActionResult> SignUpForGame(Guid userId, Guid gameId)
        {

            var game = await _context.Games.FirstOrDefaultAsync(g => g.Id == gameId);

            if (game == null)
            {
                return Ok(new { success = true, message = "Game not found" });
            }

            var existingSignUp = await _context.UserGames
                .FirstOrDefaultAsync(gp => gp.GameId == gameId && gp.UserId == userId);

            if (existingSignUp != null)
            {
                return Ok(new { success = true, message = "User is already signed up" });
            }

            var userIsInAnotherGame = await _context.UserGames
                .AnyAsync(gp => gp.UserId == userId && gp.Game.GameDate.Date == game.GameDate.Date);

            if (userIsInAnotherGame)
            {
                return Ok(new { success = true, message = "User is already signed up for another game on the same date" });
            }

            var gameParticipant = new UserGame
            {
                GameId = gameId,
                UserId = userId
            };

            _context.UserGames.Add(gameParticipant);
            await _context.SaveChangesAsync();

            return Ok(new { success = true, message = "Signed up successfully" });
        }

        [HttpGet("next/{id}")]
        public async Task<ActionResult<GameDetailDto>> GetNextGameForPlayer(Guid id)
        {
            var nextGame = await _context.UserGames
                .Where(ug => ug.UserId == id) 
                .Include(ug => ug.Game) 
                    .ThenInclude(g => g.Field)
                        .ThenInclude(f => f.Company)
                .Include(ug => ug.Game)
                    .ThenInclude(g => g.Field)
                        .ThenInclude(f => f.Country)
                .Include(ug => ug.Game)
                    .ThenInclude(g => g.Field)
                        .ThenInclude(f => f.Province)
                .Where(ug => ug.Game.GameDate >= DateTime.Now) 
                .OrderBy(ug => ug.Game.GameDate)
                .Select(ug => ug.Game) 
                .FirstOrDefaultAsync();




            if (nextGame == null)
            {
                return NotFound("No upcoming games found");
            }

            var gameDto = new GameDetailDto
            { 
                Id = nextGame.Id,
                FieldName = nextGame.Field.Name,
                ProvinceName = nextGame.Field.Province.Name,
                GameDateTime = nextGame.GameDate,
                IsAM = nextGame.IsAM,
                CurrentPlayers = _context.UserGames.Count(gp => gp.GameId == nextGame.Id),
                MaxPlayers = nextGame.MaxPlayers
            };


            return gameDto;
        }

        [HttpDelete("{userId}/cancel/{gameId}")]
        public async Task<ActionResult<bool>> CancelGameSignUp(Guid userId, Guid gameId)
        {
            var gameParticipant = await _context.UserGames
                .FirstOrDefaultAsync(gp => gp.GameId == gameId && gp.UserId == userId);

            if (gameParticipant == null)
            {
                return NotFound(false);
            }

            _context.UserGames.Remove(gameParticipant);
            await _context.SaveChangesAsync();

            return Ok(true);
        }

    }
}


