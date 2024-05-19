namespace AirsofterAPI.Entities
{
    public class Administrator
    {
        public Guid Id { get; set; }
        public required string? Username { get; set; }
        public string? DisplayName { get; set; }
        public required string? Password { get; set; }
        public required string? Email { get; set; }
    }
}
