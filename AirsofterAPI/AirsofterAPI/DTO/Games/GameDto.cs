namespace AirsofterAPI.DTO.Games
{
    public class GameDto
    {
        public Guid Id { get; set; }
        public string FieldName { get; set; }
        public string Location { get; set; }
        public DateTime GameDateTime { get; set; }
        public bool IsAM { get; set; }
        public int CurrentPlayers { get; set; }
        public int MaxPlayers { get; set; }
    }


}
