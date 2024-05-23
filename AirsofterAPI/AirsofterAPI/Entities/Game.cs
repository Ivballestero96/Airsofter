namespace AirsofterAPI.Entities
{
   public class Game
    {
        public Guid Id { get; set; }
        public string? Description { get; set; }
        public DateTime GameDate {  get; set; }
        public Guid FieldId { get; set; }
        public Field? Field { get; set; }
        public bool IsAM {  get; set; }
        public int MaxPlayers { get; set; }

        public ICollection<UserGame> UserGames { get; set; }

    }
}
