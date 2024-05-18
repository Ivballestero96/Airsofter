namespace AirsofterAPI.Entities
{
    public partial class User
    {
        public Guid Id { get; set; }
        public required string? Username { get; set; }
        public required string? DisplayName { get; set; }
        public required string? Password { get; set; }
        public required string? Email { get; set; }
        public DateTime CreationDate { get; set; }
    }
}
