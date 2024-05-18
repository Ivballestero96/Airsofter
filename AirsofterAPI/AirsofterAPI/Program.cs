
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
            ConfigureServices(builder.Services);

            var app = builder.Build();
            Configure(app, builder.Configuration);

            app.Run();
        }

        private static void ConfigureServices(IServiceCollection services)
        {
            var connectionString = services.BuildServiceProvider().GetService<IConfiguration>().GetConnectionString("DefaultConnection");

            if (connectionString == null)
            {
                throw new Exception("ConnectionString not valid");
            }

            services.AddControllers();
            services.AddEndpointsApiExplorer();
            services.AddSwaggerGen();
            services.AddEntityFrameworkMySQL().AddDbContext<AirsofterDbContext>(options =>
            {
                options.UseMySQL(connectionString);
            });

            services.AddCors(options =>
            {
                options.AddPolicy("AllowAnyOrigin", builder =>
                {
                    builder.AllowAnyOrigin()
                           .AllowAnyHeader()
                           .AllowAnyMethod();
                });
            });
        }

        private static void Configure(WebApplication app, IConfiguration configuration)
        {
            if (app.Environment.IsDevelopment())
            {
                app.UseSwagger();
                app.UseSwaggerUI();
            }

            app.UseHttpsRedirection();
            app.UseAuthorization();
            app.MapControllers();

            app.UseCors("AllowAnyOrigin");
        }
    }

}

