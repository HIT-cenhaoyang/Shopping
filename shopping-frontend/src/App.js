import './App.css';
import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate, Link } from 'react-router-dom';
import ListOrders from './ListOrders';
import ListCustomer from './ListCustomer';
import Login from './Login';
import Logout from './Logout';
import ProductList from './components/ProductList';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(localStorage.getItem('auth') === 'true');

  useEffect(() => {
    setIsLoggedIn(localStorage.getItem('auth') === 'true');
  }, []);

  const Home = () => (
    <div>
      <div className="header">
        <Logout onLogout={() => setIsLoggedIn(false)} />
        <h2>Welcome to 7Haven Admin Page</h2>
      </div>
      <nav>
        <Link to="/products">Products</Link>
        <Link to="/orders">Orders</Link>
        <Link to="/customers">Customers</Link>
      </nav>
    </div>
  );

  return (
    <Router>
      <div className="App">
        {isLoggedIn && <Home />}
        <Routes>
          <Route path="/" element={isLoggedIn ? null : <Navigate to="/login" />} />
          <Route path="/products" element={isLoggedIn ? <ProductList /> : <Navigate to="/login" />} />
          <Route path="/orders" element={isLoggedIn ? <ListOrders /> : <Navigate to="/login" />} />
          <Route path="/customers" element={isLoggedIn ? <ListCustomer /> : <Navigate to="/login" />} />
          <Route path="/login" element={<Login onLogin={() => setIsLoggedIn(true)} />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
