using AirsofterAPI.DTO.Users;
using AirsofterAPI.Entities;
using AirsofterAPI.Utils;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using MySql.Data.MySqlClient;
using System.Data;

namespace AirsofterAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly AirsofterDbContext _context;

        public UserController(AirsofterDbContext context)
        {
            this._context = context;
        }

        [HttpPost("register")]
        public async Task<ActionResult<UserToModify>> Register([FromBody] UserToModify userToModify)
        {
            try
            {
                // Validar los datos recibidos
                if (!ModelState.IsValid)
                {
                    return BadRequest(ModelState);
                }

                // Verificar si el nombre de usuario ya está en uso
                if (await _context.Users.AnyAsync(u => u.Username == userToModify.Username))
                {
                    return Ok(new { success = false, message = "Username taken" });
                }

                // Verificar si el email ya está en uso
                if (await _context.Users.AnyAsync(u => u.Email == userToModify.Email))
                {
                    return Ok(new { success = false, message = "Email taken" });
                }

                // Verificar si el alias ya está en uso
                if (await _context.Users.AnyAsync(u => u.DisplayName == userToModify.DisplayName))
                {
                    return Ok(new { success = false, message = "DisplayName taken" });
                }

                // Crear un nuevo usuario
                var newUser = new User
                {
                    Username = userToModify?.Username,
                    DisplayName = userToModify?.DisplayName,
                    Email = userToModify?.Email,
                    Password = PasswordManager.HashPassword(userToModify?.Password),
                    CreationDate = DateTime.UtcNow
                };

                // Agregar el nuevo usuario a la base de datos
                _context.Users.Add(newUser);
                await _context.SaveChangesAsync();


                return Ok(new { success = true, message = "OK" });
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Error during registration: {ex.Message}");
            }
        }

        [HttpPost("login")]
        public async Task<ActionResult<LoginResponse>> Login([FromBody] LoginRequest loginRequest)
        {
            try
            {
                // Buscar el usuario en la base de datos usando el nombre de usuario encriptado
                var user = await _context.Users.FirstOrDefaultAsync(u => u.Username == loginRequest.Username);

                if (user == null || !PasswordManager.VerifyPassword(loginRequest.Password, user.Password))
                {
                    return Unauthorized(); // Usuario no encontrado o contraseña incorrecta
                }

                // Usuario autenticado correctamente, puedes devolver la información del usuario como DTO
                var userToLoad = new UserToLoad
                {
                    Id = user.Id.ToString(),
                    Username = user.Username,
                    DisplayName = user.DisplayName,
                    Email = user.Email
                };

                var loginResponse = new LoginResponse
                {
                    UserToLoad = userToLoad
                };

                return Ok(loginResponse);
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Error during login: {ex.Message}");
            }
        }
    }
}


