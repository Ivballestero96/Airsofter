using Google.Protobuf.WellKnownTypes;

namespace AirsofterAPI.Entities
{

    public class Company
    {
        public Guid Id { get; set; }
        public string? Username { get; set; }
        public string? CompanyName { get; set; }
        public Guid CountryId { get; set; } // Clave foránea
        public Country Country { get; set; } // Relación
        public Guid ProvinceId { get; set; }
        public Province Province { get; set; }
        public string? Password { get; set; }
        public string? Email { get; set; }
        public ICollection<Field>? Fields { get; set; }
        public DateTime CreationDate { get; set; }
    }


}
