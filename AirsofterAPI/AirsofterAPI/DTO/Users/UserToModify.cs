namespace AirsofterAPI.DTO.Users
{
    public class UserToModify
    {
        public Guid Id { get; set; }
        public string? Username { get; set; }
        public string? DisplayName { get; set; }
        public string? Password { get; set; }
        public string? Email { get; set; }
        public DateTime CreationDate { get; set; }
    }
}
