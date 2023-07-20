using System.Data;
using System.Data.SqlClient;
using System.Globalization;

namespace BicycleApp
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            populateDepotGridView();
        }

        SqlConnection sqlConnection = new SqlConnection("Data Source=localhost\\SQLEXPRESS;Initial Catalog=BicycleFactory;Integrated Security = True");
        SqlDataAdapter dataAdapter = new SqlDataAdapter();
        DataSet depotDataSet = new DataSet();
        DataSet employeeDataSet = new DataSet();

        private void Form1_Load(object sender, EventArgs e)
        {
            populateDepotGridView();
        }

        private void populateDepotGridView()
        {
            try
            {
                sqlConnection.Open();
                dataAdapter.SelectCommand = new SqlCommand("select * from Depots", sqlConnection);

                depotDataSet.Clear();
                dataAdapter.Fill(depotDataSet);
                depotsGridView.DataSource = depotDataSet.Tables[0];
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            finally
            {
                if (sqlConnection.State == ConnectionState.Open)
                {
                    sqlConnection.Close();
                }
            }
        }

        private void populateEmployeesGridView()
        {
            try
            {
                sqlConnection.Open();
                dataAdapter.SelectCommand = new SqlCommand("select * from Employees " +
                    "where DepotID=@id", sqlConnection);

                int id = int.Parse(depotsGridView.SelectedRows[0].Cells["DepotID"].Value.ToString());
                dataAdapter.SelectCommand.Parameters.Add("@id", SqlDbType.Int).Value = id;
                employeeDataSet.Clear();
                dataAdapter.Fill(employeeDataSet);
                employeesGridView.DataSource = employeeDataSet.Tables[0];
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            finally
            {
                if (sqlConnection.State == ConnectionState.Open)
                {
                    sqlConnection.Close();
                }
            }
        }

        private void addButton_Click(Object sender, EventArgs e)
        {
            try
            {
                dataAdapter.InsertCommand = new SqlCommand("insert into Employees(DepotID," +
                    "FirstName, LastName, BirthDate, Income) values " +
                    "(@depotID, @firstName, @lastName, @birthDate, @income)", sqlConnection);

                int depotID = int.Parse(depotsGridView.SelectedRows[0].Cells["DepotID"].Value.ToString());
                string firstName = firstNameTextBox.Text;
                string lastName = lastNameTextBox.Text;
                DateTime birthDate = birthDatePicker.Value.Date;
                int income = int.Parse(incomeTextBox.Text);

                dataAdapter.InsertCommand.Parameters.Add("@depotID", SqlDbType.Int).Value = depotID;
                dataAdapter.InsertCommand.Parameters.Add("@firstName", SqlDbType.VarChar).Value = firstName;
                dataAdapter.InsertCommand.Parameters.Add("@lastName", SqlDbType.VarChar).Value = lastName;
                dataAdapter.InsertCommand.Parameters.Add("@birthDate", SqlDbType.Date).Value = birthDate;
                dataAdapter.InsertCommand.Parameters.Add("@income", SqlDbType.Int).Value = income;

                sqlConnection.Open();
                dataAdapter.InsertCommand.ExecuteNonQuery();
                MessageBox.Show("Inserted successfully into the database!");
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            finally
            {
                if (sqlConnection.State == ConnectionState.Open)
                {
                    sqlConnection.Close();
                    populateEmployeesGridView();
                }
            }
        }

        private void updateButton_Click(object sender, EventArgs e)
        {
            try
            {
                dataAdapter.UpdateCommand = new SqlCommand("update Employees " +
                    "set FirstName=@firstName, LastName=@lastName, BirthDate=@birthDate, Income=@income " +
                    "where EmployeeID=@employeeID", sqlConnection);

                int employeeID = int.Parse(employeesGridView.SelectedRows[0].Cells["EmployeeID"].Value.ToString());
                string firstName = firstNameTextBox.Text;
                string lastName = lastNameTextBox.Text;
                DateTime birthDate = birthDatePicker.Value.Date;
                double income = int.Parse(incomeTextBox.Text);

                dataAdapter.UpdateCommand.Parameters.Add("@employeeID", SqlDbType.Int).Value = employeeID;
                dataAdapter.UpdateCommand.Parameters.Add("@firstName", SqlDbType.VarChar).Value = firstName;
                dataAdapter.UpdateCommand.Parameters.Add("@lastName", SqlDbType.VarChar).Value = lastName;
                dataAdapter.UpdateCommand.Parameters.Add("@birthDate", SqlDbType.Date).Value = birthDate;
                dataAdapter.UpdateCommand.Parameters.Add("@income", SqlDbType.Int).Value = income;

                sqlConnection.Open();
                dataAdapter.UpdateCommand.ExecuteNonQuery();
                MessageBox.Show("Updated successfully into the database!");
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            finally
            {
                if (sqlConnection.State == ConnectionState.Open)
                {
                    sqlConnection.Close();
                    populateEmployeesGridView();
                }
            }
        }

        private void deleteButton_Click(object sender, EventArgs e)
        {
            try
            {
                dataAdapter.DeleteCommand = new SqlCommand("delete from Employees where EmployeeID=@employeeID", sqlConnection);
                int employeeID = int.Parse(employeesGridView.SelectedRows[0].Cells["EmployeeID"].Value.ToString());
                dataAdapter.DeleteCommand.Parameters.Add("@employeeID", SqlDbType.Int).Value = employeeID;
                sqlConnection.Open();
                dataAdapter.DeleteCommand.ExecuteNonQuery();
                MessageBox.Show("Deleted successfully from the database!");
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            finally
            {
                if (sqlConnection.State == ConnectionState.Open)
                {
                    sqlConnection.Close();
                    populateEmployeesGridView();
                }
            }
        }

        private void depotsGridView_RowHeaderMouseClick_1(object sender, DataGridViewCellMouseEventArgs e)
        {
            populateEmployeesGridView();
        }

        private void employeesGridView_RowHeaderMouseClick_1(object sender, DataGridViewCellMouseEventArgs e)
        {
            string firstName = employeesGridView.SelectedRows[0].Cells["FirstName"].Value.ToString();
            string lastName = employeesGridView.SelectedRows[0].Cells["LastName"].Value.ToString();
            string releaseDate = employeesGridView.SelectedRows[0].Cells["BirthDate"].Value.ToString();

            releaseDate = releaseDate.Split(" ")[0];
            string day, month, year;
            string[] arr = releaseDate.Split("/");
            if (arr[1].Length == 1)
                day = "0" + arr[1];
            else
                day = arr[1];
            if (arr[0].Length == 1)
                month = "0" + arr[0];
            else
                month = arr[0];
            year = arr[2];
            //MessageBox.Show(day + "." + month + "." + year);
            int income = int.Parse(employeesGridView.SelectedRows[0].Cells["Income"].Value.ToString());

            firstNameTextBox.Text = firstName;
            lastNameTextBox.Text = lastName;
            birthDatePicker.Value = new DateTime(int.Parse(year), int.Parse(month), int.Parse(day));
            incomeTextBox.Text = income.ToString();
        }

        
    }
}