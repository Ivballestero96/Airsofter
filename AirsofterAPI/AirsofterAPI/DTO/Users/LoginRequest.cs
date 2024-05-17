using System.ComponentModel.DataAnnotations;

namespace AirsofterAPI.DTO.Users
{
    public class LoginRequest
    {
        [Required(ErrorMessage = "Username is required")]
        public required string Username { get; set; }

        [Required(ErrorMessage = "Password is required")]
        public required string Password { get; set; }
    }
}
