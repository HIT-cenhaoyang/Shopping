import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './ModalForm.css'; // 引入CSS文件

const ProductEditForm = ({ productId, onClose }) => {
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [price, setPrice] = useState('');
  const [stockAvailable, setStockAvailable] = useState('');
  const [category, setCategory] = useState('');
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    axios.get(`http://localhost:8080/api/products/${productId}`)
      .then(response => {
        const product = response.data;
        setName(product.name);
        setDescription(product.description);
        setPrice(product.price);
        setStockAvailable(product.stockAvailable);
        setCategory(product.category);
      })
      .catch(error => {
        console.error('There was an error fetching the product!', error);
      });

    axios.get('http://localhost:8080/api/categories')
      .then(response => {
        const categories = response.data;
        setCategories(categories);
      })
      .catch(error => {
        console.error('There was an error fetching the categories!', error);
      });
  }, [productId]);

  const handleSubmit = (e) => {
    e.preventDefault();

    const updatedProduct = {
      name,
      description,
      price,
      stockAvailable,
      category
    };
    axios.put(`http://localhost:8080/api/products/${productId}`, updatedProduct)
    .then(response => {
      console.log('Product updated successfully:', response.data);
      window.location.reload();
    })
    .catch(error => {
      console.error('There was an error updating the product!', error);
      window.location.reload();
    });
  };

  return (
    <div className="modal">
      <div className="modal-content">
        <span className="close" onClick={onClose}>&times;</span>
        <form onSubmit={handleSubmit}>
          <label>Name:</label>
          <input type="text" name="name" value={name} onChange={(e) => setName(e.target.value)} /><br />
          <label>Description:</label>
          <input type="text" name="description" value={description} onChange={(e) => setDescription(e.target.value)} /><br />
          <label>Price:</label>
          <input type="text" name="price" value={price} onChange={(e) => setPrice(e.target.value)} /><br />
          <label>Stock:</label>
          <input type="number" name="stockAvailable" value={stockAvailable} onChange={(e) => setStockAvailable(e.target.value)} /><br />
          <label>Category:</label>
          <select name="category" value={category.categoryName} onChange={(e) => setCategory(e.target.value)}>
            <option value="">Select Category</option>
            {categories.map(category => (
              <option key={category.id} value={category.categoryName}>
                {category.categoryName}
              </option>
            ))}
          </select><br />
          <button type="submit">Save</button>
        </form>
      </div>
    </div>
  );
};

export default ProductEditForm;