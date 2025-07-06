
import './App.css'
import FooterComponent from './components/FooterComponent'
import HeaderComponent from './components/HeaderComponent'
import ListExpenseComponent from './components/ListExpenseComponent'
import ExpenseComponent from './components/ExpenseComponent'
import {BrowserRouter,Routes,Route} from 'react-router-dom'


function App() {


  return (
    <>
      <BrowserRouter>
        <HeaderComponent></HeaderComponent>
          <Routes>
            {/* // http://localhost:3000 */}
              <Route path="/" element={<ListExpenseComponent/>}></Route>
            {/* // http://localhost:3000/expense */}
              <Route path="/expense" element={<ListExpenseComponent/>}></Route>
            {/* // http://localhost:3000/add-expense */}
              <Route path="/add-expense" element={<ExpenseComponent/>}></Route>
            {/* // http://localhost:3000/update-expense/1 */}
              <Route path="/update-expense/:id" element={<ExpenseComponent/>}></Route>

          </Routes>
        <FooterComponent/>
      </BrowserRouter>
    </>
  )
}

export default App
