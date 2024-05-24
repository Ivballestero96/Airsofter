using System.ComponentModel.DataAnnotations;

namespace AirsofterAPI.DTO.Games
{
    public class CreateGameDto
    {
        [Required]
        public Guid FieldId { get; set; } 

        [Required]
        public string Description { get; set; } 

        [Required]
        public DateTime GameDate { get; set; } 

        [Required]
        public bool IsAM { get; set; }  

        [Required]
        [Range(1, int.MaxValue)]
        public int MaxPlayers { get; set; }  
    }

}
