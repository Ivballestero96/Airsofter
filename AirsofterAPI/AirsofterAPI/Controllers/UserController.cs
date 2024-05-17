using AirsofterAPI.DTO;
using AirsofterAPI.Entities;
using AirsofterAPI.Utils;
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

        [HttpGet("GetUser/{id}")]
        public async Task<ActionResult<UserDTO>> GetUser(int id)
        {
            try
            {
                var user = await _context.Users.FindAsync(id);

                if (user == null)
                {
                    return NotFound();
                }

                var userDTO = new UserDTO
                {
                    Id = user.Id,
                    Username = user.Username,
                    DisplayName = user.DisplayName,
                    Email = user.Email,
                    CreationDate = user.CreationDate
                };

                return Ok(userDTO);
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Error retrieving user: {ex.Message}");
            }
        }


        [HttpPost("Register")]
        public async Task<ActionResult<UserDTO>> Register([FromBody] UserDTO userDTO)
        {
            try
            {
                // Validar los datos recibidos
                if (!ModelState.IsValid)
                {
                    return BadRequest(ModelState);
                }

                // Verificar si el nombre de usuario ya está en uso
                if (await _context.Users.AnyAsync(u => u.Username == userDTO.Username))
                {
                    return BadRequest(new { success = true, message = "El nombre de usuario ya está en uso"});
                }

                // Crear un nuevo usuario
                var newUser = new User
                {
                    Username = userDTO?.Username,
                    DisplayName = userDTO?.DisplayName,
                    Email = userDTO?.Email,
                    Password = PasswordManager.HashPassword(userDTO?.Password),
                    CreationDate = DateTime.UtcNow
                };

                // Agregar el nuevo usuario a la base de datos
                _context.Users.Add(newUser);
                await _context.SaveChangesAsync();

                return Ok(new {success = true, message = "Registro exitoso"});
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Error during registration: {ex.Message}");
            }
        }




        [HttpPost("login")]
        public async Task<ActionResult<UserDTO>> Login([FromBody] LoginRequest loginRequest)
        {
            try
            {
                var user = await _context.Users.FirstOrDefaultAsync(u => u.Username == loginRequest.Username);
                var hashedPassword = PasswordManager.HashPassword(loginRequest.Password);
                if (user == null || !PasswordManager.VerifyPassword(hashedPassword, user.Password))
                {
                    return Unauthorized(); // Usuario no encontrado o contraseña incorrecta
                }

                // Usuario autenticado correctamente, puedes devolver la información del usuario como DTO
                var userDTO = new UserDTO
                {
                    Id = user.Id,
                    Username = user.Username,
                    DisplayName = user.DisplayName,
                    Email = user.Email,
                    CreationDate = user.CreationDate
                };

                return Ok(userDTO);
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Error during login: {ex.Message}");
            }
        }

    }
}
