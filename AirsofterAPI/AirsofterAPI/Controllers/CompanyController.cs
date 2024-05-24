using AirsofterAPI.DTO.Administrators;
using AirsofterAPI.Entities;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Linq;

namespace AirsofterAPI.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class CompanyController : ControllerBase
    {
        private readonly AirsofterDbContext _context;

        public CompanyController(AirsofterDbContext context)
        {
            _context = context;
        }

        [HttpPost("login")]
        public IActionResult Login([FromBody] CompanyLoginDto model)
        {
            var company = _context.Companies.SingleOrDefault(a => a.Username == model.Username && a.Password == model.Password);

            if (company == null)
            {
                return Unauthorized(new { message = "Usuario o contraseña inválidos" });
            }

            // Crear un objeto anónimo con la información que deseas devolver
            var response = new
            {
                company.Id,
                company.Username,
                company.CompanyName,
                company.Email
            };

            return Ok(response);
        }


        [HttpGet("{id}")]
        public IActionResult GetById(Guid id)
        {
            var admin = _context.Companies.Find(id);

            if (admin == null)
            {
                return NotFound();
            }

            return Ok(admin);
        }

        [HttpGet("username/{username}")]
        public IActionResult GetByUserName(string username)
        {
            var admin = _context.Companies.SingleOrDefault(a => a.Username == username);

            if (admin == null)
            {
                return NotFound();
            }

            return Ok(admin);
        }
    }
}
