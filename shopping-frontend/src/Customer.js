import React from "react";

export default function Customer({customer}) {
    return (
        <>
            <tr>
                <td>{customer.customerId}</td>
                <td>{customer.userName}</td>
                <td>{customer.gender}</td>
                <td>{customer.birthDate}</td>
                <td>{customer.phoneNumber}</td>
                <td>{customer.email}</td>
            </tr>
        </>
    )
}