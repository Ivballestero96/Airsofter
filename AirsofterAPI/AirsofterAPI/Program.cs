
using AirsofterAPI.Entities;
using AirsofterAPI.Utils;
using Microsoft.EntityFrameworkCore;
using MySql.EntityFrameworkCore.Extensions;

namespace AirsofterAPI
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);
            var connectionString = builder.Configuration.GetConnectionString("DefaultConnection");

            if (connectionString != null)
            {
                builder.Services.AddControllers();
                builder.Services.AddEndpointsApiExplorer();
                builder.Services.AddSwaggerGen();
                builder.Services.AddEntityFrameworkMySQL()
                    .AddDbContext<AirsofterDbContext>(options =>
                    {
                        options.UseMySQL(connectionString);
                    });

                var app = builder.Build();

                if (app.Environment.IsDevelopment())
                {
                    app.UseSwagger();
                    app.UseSwaggerUI();
                }

                app.UseHttpsRedirection();
                app.UseAuthorization();
                app.MapControllers();
                app.Run();
            }
            else
            {
                throw new Exception("ConnectionString not valid");
            }
        }
    }
}
