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
            populateParentGridView();
        }

        SqlConnection sqlConnection = new SqlConnection("Data Source=localhost\\SQLEXPRESS;Initial Catalog=ExamDBMS;Integrated Security = True");
        SqlDataAdapter dataAdapter = new SqlDataAdapter();
        DataSet parentDataSet = new DataSet();
        DataSet childDataSet = new DataSet();

        private void Form1_Load(object sender, EventArgs e)
        {
            populateParentGridView();
        }

        private void populateParentGridView()
        {
            try
            {
                sqlConnection.Open();
                dataAdapter.SelectCommand = new SqlCommand("select * from Sports", sqlConnection);

                parentDataSet.Clear();
                dataAdapter.Fill(parentDataSet);
                dgvSports.DataSource = parentDataSet.Tables[0];
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

        private void populateChildGridView()
        {
            try
            {
                sqlConnection.Open();
                dataAdapter.SelectCommand = new SqlCommand("select * from Teams " +
                    "where SportID=@id", sqlConnection);

                int id = int.Parse(dgvSports.SelectedRows[0].Cells["SportID"].Value.ToString());
                dataAdapter.SelectCommand.Parameters.Add("@id", SqlDbType.Int).Value = id;
                childDataSet.Clear();
                dataAdapter.Fill(childDataSet);
                dgvTeams.DataSource = childDataSet.Tables[0];
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
                dataAdapter.InsertCommand = new SqlCommand("insert into Teams(SportID, TeamTypeID," +
                    "NameTeam, NumberOfMembers, StartUpYear, City) values " +
                    "(@parentID, @teamTypeID, @name, @noMembers, @startUpYear, @city)", sqlConnection);

                int parentID = int.Parse(dgvSports.SelectedRows[0].Cells["SportID"].Value.ToString());
                string name = nameTextBox.Text;
                int numberOfMembers = int.Parse(numberOfMembersTextBox.Text);
                int startUpYear = int.Parse(startUpYearTextBox.Text);
                int teamType = int.Parse(teamTypeTextBox.Text);
                string city = cityTextBox.Text;

                dataAdapter.InsertCommand.Parameters.Add("@parentID", SqlDbType.Int).Value = parentID;
                dataAdapter.InsertCommand.Parameters.Add("@teamTypeID", SqlDbType.Int).Value = teamType;
                dataAdapter.InsertCommand.Parameters.Add("@name", SqlDbType.VarChar).Value = name;
                dataAdapter.InsertCommand.Parameters.Add("@noMembers", SqlDbType.Int).Value = numberOfMembers;
                dataAdapter.InsertCommand.Parameters.Add("@startUpYear", SqlDbType.Int).Value = startUpYear;
                dataAdapter.InsertCommand.Parameters.Add("@city", SqlDbType.VarChar).Value = city;

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
                    populateChildGridView();
                }
            }
        }

        private void updateButton_Click(object sender, EventArgs e)
        {
            try
            {
                dataAdapter.UpdateCommand = new SqlCommand("update Teams" +
                    "set NameTeam=@name, NumberOfMembers=@numberOfMembers, StartUpYear=@startUpYear, City=@city, TeamTypeID=@teamTypeID " +
                    "where TeamID=@childID", sqlConnection);

                int childID = int.Parse(dgvTeams.SelectedRows[0].Cells["TeamID"].Value.ToString());
                string name = nameTextBox.Text;
                int numberOfMembers = int.Parse(numberOfMembersTextBox.Text);
                int startUpYear = int.Parse(startUpYearTextBox.Text);
                int teamType = int.Parse(teamTypeTextBox.Text);
                string city = cityTextBox.Text;

                dataAdapter.UpdateCommand.Parameters.Add("@childID", SqlDbType.Int).Value = childID;
                dataAdapter.UpdateCommand.Parameters.Add("@teamTypeID", SqlDbType.Int).Value = teamType;
                dataAdapter.UpdateCommand.Parameters.Add("@name", SqlDbType.VarChar).Value = name;
                dataAdapter.UpdateCommand.Parameters.Add("@noMembers", SqlDbType.Int).Value = numberOfMembers;
                dataAdapter.UpdateCommand.Parameters.Add("@startUpYear", SqlDbType.Int).Value = startUpYear;
                dataAdapter.UpdateCommand.Parameters.Add("@city", SqlDbType.VarChar).Value = city;

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
                    populateChildGridView();
                }
            }
        }

        private void deleteButton_Click(object sender, EventArgs e)
        {
            try
            {
                dataAdapter.DeleteCommand = new SqlCommand("delete from Teams where TeamID=@childID", sqlConnection);
                int childID = int.Parse(dgvTeams.SelectedRows[0].Cells["TeamID"].Value.ToString());
                dataAdapter.DeleteCommand.Parameters.Add("@childID", SqlDbType.Int).Value = childID;
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
                    populateChildGridView();
                }
            }
        }

        private void depotsGridView_RowHeaderMouseClick_1(object sender, DataGridViewCellMouseEventArgs e)
        {
            populateChildGridView();
        }

        private void employeesGridView_RowHeaderMouseClick_1(object sender, DataGridViewCellMouseEventArgs e)
        {
            string name = dgvTeams.SelectedRows[0].Cells["NameTeam"].Value.ToString();
            int startUpYear = int.Parse(dgvTeams.SelectedRows[0].Cells["StartUpYear"].Value.ToString());
            string city = dgvTeams.SelectedRows[0].Cells["City"].Value.ToString();
            int teamType = int.Parse(dgvTeams.SelectedRows[0].Cells["TeamTypeID"].Value.ToString());
            int numberOFMembers = int.Parse(dgvTeams.SelectedRows[0].Cells["NumberOfMembers"].Value.ToString());

            nameTextBox.Text = name;
            numberOfMembersTextBox.Text = numberOFMembers.ToString();
            cityTextBox.Text = city;
            teamTypeTextBox.Text = teamType.ToString();
            startUpYearTextBox.Text = startUpYear.ToString();
        }

        
    }
}