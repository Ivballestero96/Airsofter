using AirsofterAPI.DTO;
using AirsofterAPI.Entities;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using MySql.Data.MySqlClient;
using System.Data;

namespace AirsofterAPI.Controllers
{
    [Route("api/users")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly AirsofterDbContext _context;

        public UserController(AirsofterDbContext context)
        {
            this._context = context;
        }

        [HttpGet("GetUsers")]
        public async Task<ActionResult<List<UserDTO>>> Get([FromServices] IConfiguration config)
        {
            try
            {
                var users = await _context.Users.ToListAsync();
                var userDTOs = users.Select(u => new UserDTO
                {
                    Username = u.Username,
                    DisplayName = u.DisplayName,
                    Email = u.Email,
                    CreationDate = u.CreationDate
                }).ToList();

                if (userDTOs.Count > 0)
                {
                    return Ok(userDTOs);
                }
                else
                {
                    return NotFound();
                }
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Error retrieving users: {ex.Message}");
            }
        }

        //TODO Metodos restantes y encriptado
    }
}
