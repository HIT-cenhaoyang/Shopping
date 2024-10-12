import { useState, useEffect } from "react";
import Order from './Order';

export default function ListOrders({orderList}) {
    const [orders, setOrders] = useState([]);
    const [error, setError] = useState(null);
    const [orderId, setOrderId] = useState('');
    const [customerId, setCustomerId] = useState('');
    const [productId, setProductId] = useState('');
    const [date, setDate] = useState('');

    useEffect(() => {
        let url = 'http://localhost:8080/api/orders?';
        if (orderId) url += `orderId=${orderId}&`;
        if (customerId) url += `customerId=${customerId}&`;
        if (productId) url += `productId=${productId}&`;
        if (date) url += `date=${date}`;

        fetch(url) 
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch orders');
                }
                return response.json();
            })
            .then(data => {
                setOrders(data);
                setError(null);
            })
            .catch(error => setError(error.message));
    }, [orderId, customerId, productId, date]);

    const clearFilters = () => {
        setOrderId('');
        setCustomerId('');
        setProductId('');
        setDate('');
    };
    return (
        <div>
            <div>
                <h4>Filter Options</h4>
                <label>
                Order Id: <input type = "text" value = {orderId} onChange= {(input) => setOrderId(input.target.value)} />
                </label>
                <label>
                Customer Id: <input type = "text" value={customerId} onChange = {(input) => setCustomerId(input.target.value)} />
                </label>
                <label>
                Product Id: <input type = "text" value={productId} onChange = {(input) => setProductId(input.target.value)} />
                </label>
                <label>
                Date: <input type = "date" value = {date} onChange= { (input) => setDate(input.target.value)} />
                </label>
                <button onClick={clearFilters}>Clear All</button>
                {error && <p style={{ color: 'red' }}>{error}</p>}
                <h3>Purchase Records</h3>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Order Id</th>
                        <th>Customer Id</th>
                        <th>Order Date</th>
                        <th>Order Total</th>
                        <th>Product Id</th>
                        <th>Product Name</th>
                        <th>Product Price</th>
                        <th>Product Qty</th>
                        <th>Subtotal</th>
                    </tr>
                </thead>
                <tbody>
                    {orders.map(order => (
                        <Order key={order.orderId} order={order} />
                    ))}
                </tbody>
            </table>
        </div>
    );
}