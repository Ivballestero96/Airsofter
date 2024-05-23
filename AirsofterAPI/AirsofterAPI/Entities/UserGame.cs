namespace AirsofterAPI.Entities
{
    public class UserGame
    {
        public Guid UserId { get; set; }
        public User User { get; set; }

        public Guid GameId { get; set; }
        public Game Game { get; set; }

        // Clave primaria compuesta
        public override int GetHashCode()
        {
            return HashCode.Combine(UserId, GameId);
        }

        public override bool Equals(object obj)
        {
            if (obj == null || !(obj is UserGame))
                return false;

            var other = obj as UserGame;
            return UserId == other.UserId && GameId == other.GameId;
        }
    }
}
