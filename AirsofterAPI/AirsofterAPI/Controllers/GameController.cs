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
                CurrentPlayers = 0, // Calcular por tabla de enlace
                MaxPlayers = g.MaxPlayers
            }).ToList();

            var gameListResponse = new GameListResponse
            {
                GameListDto = gameDtos
            };

            return gameListResponse;
        }


        [HttpGet("{id}")]
        public async Task<ActionResult<Game>> GetGame(Guid id)
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

            return game;
        }
    }
}
