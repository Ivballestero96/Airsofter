﻿using Microsoft.EntityFrameworkCore;

namespace AirsofterAPI.Entities
{
    public partial class AirsofterDbContext : DbContext
    {
        public AirsofterDbContext() { }

        public AirsofterDbContext(DbContextOptions<AirsofterDbContext> options) : base(options) { }

        public virtual DbSet<Administrator> Administrators { get; set; }
        public virtual DbSet<User> Users { get; set; }
        public virtual DbSet<Company> Companies { get; set; }
        public virtual DbSet<Country> Countries { get; set; }
        public virtual DbSet<Province> Provinces { get; set; }
        public virtual DbSet<Field> Fields { get; set; }
        public virtual DbSet<Game> Games { get; set; }
        public virtual DbSet<UserGame> UserGames { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder) { }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<UserGame>().HasNoKey();
        }

    }
}
