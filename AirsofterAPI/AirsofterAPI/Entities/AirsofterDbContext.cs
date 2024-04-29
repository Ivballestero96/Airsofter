using Microsoft.EntityFrameworkCore;

namespace AirsofterAPI.Entities
{
    public partial class AirsofterDbContext : DbContext
    {
        public AirsofterDbContext() { }

        public AirsofterDbContext(DbContextOptions<AirsofterDbContext> options) : base(options) { }

        public virtual DbSet<User> Users { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder) { }

    }
}
