namespace AirsofterAPI.Utils
{
    public class PasswordManager
    {
        // Método para encriptar una contraseña
        public static string HashPassword(string? password)
        {
            if (password == null)
            {
                throw new ArgumentNullException(nameof(password));
            }

            // Genera un hash bcrypt para la contraseña
            return BCrypt.Net.BCrypt.HashPassword(password);
        }

        // Método para verificar si una contraseña coincide con su hash
        public static bool VerifyPassword(string? password, string? hashedPassword)
        {
            // Verifica si tanto password como hashedPassword no son nulos
            if (password == null || hashedPassword == null)
            {
                throw new ArgumentNullException("La contraseña o el hash de la contraseña son nulos.");
            }

            // Verifica si la contraseña coincide con su hash
            return BCrypt.Net.BCrypt.Verify(password, hashedPassword);
        }

    }
}
