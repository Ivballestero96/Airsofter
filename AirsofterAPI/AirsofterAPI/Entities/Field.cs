namespace AirsofterAPI.Entities
{
    public class Field
    {
        public Guid Id { get; set; }
        public string Name { get; set; }
        public Guid CompanyId {  get; set; }
        public Company Company {  get; set; }
        public Guid CountryId {  get; set; }
        public Country Country { get; set; }
        public Guid ProvinceId {  get; set; }
        public Province province {  get; set; }
    }
}
