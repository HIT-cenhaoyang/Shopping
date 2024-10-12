import React from "react";
import OrderDetail from './OrderDetail';

export default function Order({order}) {

    const decimalFormat = (amount) => {
        return '$'+amount.toLocaleString(undefined, {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2,
        });
    }
    //const orderDetails = order.orderDetails || [];
    return (
        <>
            <tr className = "order-group">
                <td rowSpan={order.orderDetails.length +1}>{order.orderId}</td>
                <td rowSpan={order.orderDetails.length +1}>{order.custId}</td>
                <td rowSpan={order.orderDetails.length +1}>{order.purchaseDate}</td>
                <td rowSpan={order.orderDetails.length +1}>{decimalFormat(order.orderTotal)}</td>
            </tr>
            {order.orderDetails.map((detail, index) => (
                <tr key = {index} className = "order-detail-row">
                    <OrderDetail key={index} detail={detail} />
                </tr>
            ))}
        </>
    );
}