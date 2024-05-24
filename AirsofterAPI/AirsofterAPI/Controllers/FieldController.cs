using AirsofterAPI.Entities;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AirsofterAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class FieldController : ControllerBase
    {
        private readonly AirsofterDbContext _context;

        public FieldController(AirsofterDbContext context)
        {
            _context = context;
        }

        [HttpGet("{companyId}")]
        public async Task<ActionResult<List<Field>>> GetFieldsByCompany(Guid companyId)
        {
            var fields = await _context.Fields
                .Where(f => f.CompanyId == companyId)
                .ToListAsync();

            if (fields == null || fields.Count == 0)
            {
                return NotFound("No fields found for the specified company.");
            }

            return fields;
        }
    }
}
