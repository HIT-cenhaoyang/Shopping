import React from 'react';

export default function OrderDetail({detail}) {
    const decimalFormat = (amount) => {
        return '$'+amount.toLocaleString(undefined, {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2,
        });
    }
    return (
        <>
            <td>{detail.productId}</td>
            <td>{detail.productName}</td>
            <td>{decimalFormat(detail.productPrice)}</td>
            <td>{detail.productQty}</td>
            <td>{decimalFormat(detail.orderSubTotal)}</td> 
        </>
    )
}