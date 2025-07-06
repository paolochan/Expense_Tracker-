import React, { use, useEffect, useState } from 'react'
import { createExpense, getExpense, updateExpense } from '../services/ExpesneService'
import { useNavigate,useParams } from 'react-router-dom'

const ExpenseComponent = () => {

    const [description, setDescription] = useState('')
    const [amount, setAmount] = useState(0)
    const [categories, setCategories] = useState([])
    const [date, setDate] = useState(''); // YYYY-MM-DD format
    const allCategories = ['Food', 'Transportation', 'Entertainment'];
    const [errors, setErrors ] = useState({
        description: '',
        amount: '',

    })
    const {id} = useParams(); 


    const navigator = useNavigate();

    useEffect(() => {

        if(id){
            getExpense(id).then((response) => {
                const expense = response.data;
                setDescription(expense.description);
                setAmount(expense.amount);
                setDate(expense.date);
            }).catch(error => {
                console.error("There was an error fetching the expense!", error);
            })
        }

    }, [id])

    function handleDescription(event) {
        setDescription(event.target.value);

    }
    function handleAmount(event) {
        setAmount(event.target.value);
    }
    function handleDate(event) {
        setDate(event.target.value);
    }
    function handleCategories(event) {
        const { value, checked } = event.target;
        if (checked) {
            setCategories([...categories, value]);
        } else {
            setCategories(categories.filter(category => category !== value));
        }
    }
    function saveOrUpdateExpense(event) {
    event.preventDefault();
    if (validateForm()) {

        if(id){
            updateExpense(id, { description, amount,date, categories })
                .then((response) => {
                    console.log('Expense updated successfully:', response.data);
                    navigator('/expense');
                }).catch((error) => {
                    console.error('There was an error updating the expense!', error);
                })
        }else{
           const currentDate = new Date().toISOString().slice(0, 10); // YYYY-MM-DD
            const expense = { description, amount, date: currentDate, categories };
            console.log('Saved Expense:', expense);
            createExpense(expense).then((response) => {
                console.log('Expense created successfully:', response.data);
                navigator('/expense');

         }).catch((error) => {
                console.error('There was an error creating the expense!', error);
            })
        }
    }}

    function validateForm() {
        let valid =true;
        const errorsCopy = {... errors}
        if(description.trim()) {
            errorsCopy.description = '';
        } else { 
            errorsCopy.description = 'Description is required';
            valid = false;

        }
        if(amount > 0) {
            errorsCopy.amount = '';
        } else {
            errorsCopy.amount = 'Amount must be greater than 0';
            valid = false;
        }
        setErrors(errorsCopy);
        return valid;
    }

    function pageTitle() {
        if(id){
            return <h2 className='text-center mt-2'>Update Expense</h2>

        }else{
            return <h2 className='text-center mt-2'>Add Expense</h2>
        } 

    }


  return (
    <div className='container'>
        <br />
        <div className='row'>
            <div className='card col-md-6 offset-md-3'>
                {pageTitle()}
                <div>
                    <form action="">
                        <div className='form-group mb-2'>
                            <label className='form-label'>Description:</label>
                            <input 
                                type="text" 
                                placeholder='Enter Expense Description' 
                                name='description' 
                                className={
                                    `form-control ${errors.description ? 'is-invalid' : ''}`
                                }
                                value={description}
                                onChange={handleDescription}
                            />
                            {errors.description && (
                                <div className='invalid-feedback'>{errors.description}</div>
                            )}
                        </div>

                        <div className='form-group mb-2'>
                            <label className='form-label'>Amount:</label>
                            <input 
                                type="number"
                                placeholder='Enter Expense Amount' 
                                name='Amount' 
                                className={`form-control ${errors.amount ? 'is-invalid' : '' }` }
                                value={amount}
                                onChange={handleAmount}
                            />
                            {errors.amount && (
                                <div className='invalid-feedback'>{errors.amount}</div>
                            )}
                        </div>

                        <div className='form-group mb-2'>
                            <label className='form-label'>Categories:</label>
                            <div className='border p-2 rounded' style={{ maxHeight: '150px', overflowY: 'auto' }}>
                                {allCategories.map((category) => (
                                    <div className='form-check' key ={category}>
                                        <input type="checkbox" 
                                        className='form-check-input'
                                        value={category}
                                        checked={categories.includes(category)}
                                        onChange={handleCategories}
                                        />
                                        <label className='form-check-label'> {category}</label>
                                    </div>
                                ))}

                            </div>
                        </div>

                                

                        <button className='btn btn-success mb-2' onClick={saveOrUpdateExpense}>Submit</button>

                    </form>
                </div>

            </div>

        </div>
        
    </div>
  )
}

export default ExpenseComponent;