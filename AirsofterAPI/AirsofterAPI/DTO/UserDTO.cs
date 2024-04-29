namespace AirsofterAPI.DTO
{
    public class UserDTO
    {
        public int Id { get; set; }
        public string? Username { get; set; }
        public string? DisplayName { get; set; }
        public string? Password { get; set; }
        public string? Email { get; set; }
        public DateTime CreationDate { get; set; }

    }
}
