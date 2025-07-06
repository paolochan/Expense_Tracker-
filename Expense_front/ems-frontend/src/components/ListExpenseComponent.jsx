import React, {use, useEffect, useState} from 'react'
import { deleteExpense,listExpenses } from '../services/ExpesneService'
import { useNavigate } from 'react-router-dom'



const ListExpenseComponent = () => {

    const [expenses, setExpenses] = useState([])

    const navigator = useNavigate()

    useEffect(() => 
        {getAllExpenses()},[])

    function getAllExpenses() {
        listExpenses().then((response) => {
            setExpenses(response.data)
        }).catch(error => {
            console.error("There was an error fetching the expenses!", error);
        })
    }

    function addNewExpense() {
        navigator('/add-expense')

    }
    function updateExpense(id) {
        navigator(`/update-expense/${id}`)

    }
    function removeExpense(id) {
        console.log("Delete expense with id: " + id)

        deleteExpense(id).then((response) => {
            getAllExpenses();

        }).catch(error => {
            console.error("There was an error deleting the expense!", error);
        })
    }

  return (

    <div className='container'>

        <h2 className='text-center'>List of Expenses</h2>
        <button type="button" className="btn btn-primary mb-2" onClick={addNewExpense}>Add Expense</button>
        <table className='table table-striped table-bordered'>
            <thead>
                <tr>
                    <th>Expense Description</th>
                    <th>Expense Amount</th>
                    <th>Expense Date</th>
                    <th>Expense Categories</th>
                    <th>Actions</th>

                </tr>
            </thead>
            <tbody>
                {
                expenses.map(expense => 
                    <tr key ={expense.id}>
                        <td>{expense.description}</td>
                        <td>{expense.amount}</td>
                        <td>{expense.date}</td>
                        <td>{expense.categories.join(', ')}</td>
                        <td>
                            <button className='btn btn-info' onClick={() => updateExpense(expense.id)}>Update</button>
                            <button className='btn btn-danger' style={{marginLeft: '10px'}}  onClick={() => removeExpense(expense.id)}>Delete</button>
                        </td>

                    </tr>
                )
                }

            </tbody>
        </table>
    </div>
  )
}

export default ListExpenseComponent