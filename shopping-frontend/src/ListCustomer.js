import { useState, useEffect } from "react";
import Customer from './Customer';

export default function ListCustomers({customerList}) {
    const [customers, setCustomers] = useState([]);
    const [error, setError] = useState(null);
    const [customerId, setCustomerId] = useState('');
    const [userName, setUserName] = useState('');
    const [birthMonth, setBirthMonth] = useState('');

    useEffect(() => {
        let url = 'http://localhost:8080/api/customers?';
        if (customerId) url += `customerId=${customerId}&`;
        if (userName) url += `userName=${userName}&`;
        if (birthMonth) url += `birthMonth=${birthMonth}`;

    fetch(url)
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to fetch customers');
        }
        return response.json();
    })
    .then(data => {
        setCustomers(data);
        setError(null);
    })
    .catch(error => setError(error.message));
    }, [customerId, userName, birthMonth]);

    const clearFilters = () => {
        setCustomerId('');
        setUserName('');
        setBirthMonth('');
    };

    return (
        <div>
            <div>
                <h4>Filter Options</h4>
                <label>
                Customer Id: <input type = "text" value = {customerId} onChange= {(input) => setCustomerId(input.target.value)} />
                </label>
                <label>
                UserName: <input type = "text" value={userName} onChange = {(input) => setUserName(input.target.value)} />
                </label>
                <label>
                Birthday Month: <input type = "text" value = {birthMonth} onChange= { (input) => setBirthMonth(input.target.value)} />
                </label>
                <button onClick={clearFilters}>Clear All</button>
                {error && <p style={{ color: 'red' }}>{error}</p>}
                <h3>Customer Records</h3>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Customer Id</th>
                        <th>Username</th>
                        <th>Gender</th>
                        <th>BirthDate</th>
                        <th>Phone Number</th>
                        <th>Email</th>
                    </tr>
                </thead>
                <tbody>
                    {customers.map(customer => (
                        <Customer key={customer.customerId} customer={customer} />
                    ))}
                </tbody>
            </table>
        </div>
    );
}