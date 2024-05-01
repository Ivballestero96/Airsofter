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
                    return BadRequest("Username is already taken.");
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

                // Devolver el DTO del usuario creado
                var userDTOWithoutPassword = new UserDTO
                {
                    Id = newUser.Id,
                    Username = newUser.Username,
                    DisplayName = newUser.DisplayName,
                    Email = newUser.Email,
                    CreationDate = newUser.CreationDate
                };

                return CreatedAtAction(nameof(GetUser), new { id = newUser.Id }, userDTOWithoutPassword);
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Error during registration: {ex.Message}");
            }
        }




        [HttpPost("Login")]
        public async Task<ActionResult<UserDTO>> Login([FromBody] UserDTO loginDTO)
        {
            try
            {
                var user = await _context.Users.FirstOrDefaultAsync(u => u.Username == loginDTO.Username);

                if (user == null || !PasswordManager.VerifyPassword(loginDTO.Password, user.Password))
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
