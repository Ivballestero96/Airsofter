namespace AirsofterAPI.DTO.Games
{
    public class GameDetailDto
    {
        public Guid Id { get; set; }
        public required string FieldName { get; set; }
        public string? CompanyName { get; set; }
        public string? ProvinceName { get; set; }
        public string? CountryName { get; set; }
        public string? Description { get; set; }
        public int CurrentPlayers { get; set; }
        public int MaxPlayers { get; set; }
        public DateTime GameDateTime { get; set; }
        public bool IsAM { get; set; }
        public List<string>? Players { get; set; }
    }
}
