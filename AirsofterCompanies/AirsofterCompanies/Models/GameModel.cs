using System;

namespace AirsofterCompanies.Models
{
    public class GameModel
    {
        public string FieldId { get; set; }
        public string Description { get; set; }
        public DateTime GameDate { get; set; }
        public bool IsAM { get; set; }
        public int MaxPlayers { get; set; }
    }
}
