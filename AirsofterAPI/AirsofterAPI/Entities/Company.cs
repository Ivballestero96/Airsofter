using Google.Protobuf.WellKnownTypes;

namespace AirsofterAPI.Entities
{
    public class Company
    {
        public Guid Id { get; set; }
        public required string? Username { get; set; }
        public required string? CompanyName { get; set; }
        public string? Country { get; set; }
        public string? Province { get; set; }
        public required string? Password { get; set; }
        public required string? Email { get; set; }
        public ICollection<Field> Fields { get; set; }
        public DateTime CreationDate { get; set; }
    }
}
