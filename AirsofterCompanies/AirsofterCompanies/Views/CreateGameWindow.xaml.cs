using AirsofterCompanies.Repositories;
using AirsofterCompanies.ViewModels;
using System.Windows;
using System.Windows.Input;

namespace AirsofterCompanies.Views
{
   
    public partial class CreateGameWindow : Window
    {
        public CreateGameWindow()
        {
            InitializeComponent();
            ResizeMode = ResizeMode.NoResize; 
            SizeToContent = SizeToContent.WidthAndHeight; 

            var viewModel = new CreateGameViewModel();
            DataContext = viewModel;

            viewModel.FieldRepository = new FieldRepository();
        }

        private void Window_MouseDown(object sender, MouseButtonEventArgs e)
        {
            if (e.LeftButton == MouseButtonState.Pressed)
                DragMove();
        }

        private void btnMinimize_Click(object sender, RoutedEventArgs e)
        {
            WindowState = WindowState.Minimized;
        }

        private void btnClose_Click(object sender, RoutedEventArgs e)
        {
            Close();
        }
    }
}
