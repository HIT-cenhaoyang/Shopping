import React, { useState, useEffect } from 'react';
import axios from 'axios';
import ProductForm from './ProductForm';
import ProductEditForm from './ProductEditForm';

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [filteredProducts, setFilteredProducts] = useState([]);
  const [filter, setFilter] = useState({
    productId: '',
    name: '',
    price: '',
    stock: ''
  });
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [currentProductId, setCurrentProductId] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios.get('http://localhost:8080/api/products')
      .then(response => {
        setProducts(response.data);
        setFilteredProducts(response.data);
      })
      .catch(error => {
        setError('Error fetching products');
      });
  }, []);

  const deleteProduct = (id) => {
    axios.delete(`http://localhost:8080/api/products/${id}`)
      .then(response => {
        setProducts(products.filter(product => product.productId !== id));
      })
      .catch(error => {
        console.error('There was an error deleting the product!', error);
      });
  };

  const handleFilterChange = (e) => {
    const { name, value } = e.target;
    setFilter({ ...filter, [name]: value });
  };

  const applyFilters = () => {
    let filtered = products;

    if (filter.productId) {
      filtered = filtered.filter(product => product.productId.toString()===(filter.productId));
    }

    if (filter.name) {
      filtered = filtered.filter(product => product.name.toLowerCase().includes(filter.name.toLowerCase()));
    }

    if (filter.price) {
      filtered = filtered.filter(product => product.price.toString().includes(filter.price));
    }

    if (filter.stock) {
      filtered = filtered.filter(product => product.stock.toString().includes(filter.stock));
    }

    setFilteredProducts(filtered);
  };

  const clearFilters = () => {
    setFilter({
      productId: '',
      name: '',
      price: '',
      stock: ''
    });
    setFilteredProducts(products);
  };

  useEffect(() => {
    applyFilters();
  }, [filter]);

  const toggleForm = () => {
    setShowForm(!showForm);
  };

  const openEditModal = (productId) => {
    setCurrentProductId(productId);
    setIsEditModalOpen(true);
  };

  const closeEditModal = () => {
    setIsEditModalOpen(false);
    setCurrentProductId(null);
  };

  return (
    <div>
      <h4>Filter Options</h4>
      <label>
        Product Id: <input type="text" name="productId" value={filter.productId} onChange={handleFilterChange} />
      </label>
      <label>
        Name: <input type="text" name="name" value={filter.name} onChange={handleFilterChange} />
      </label>
      <label>
        Price: <input type="text" name="price" value={filter.price} onChange={handleFilterChange} />
      </label>
      <label>
        Stock: <input type="text" name="stock" value={filter.stock} onChange={handleFilterChange} />
      </label>
      <button onClick={clearFilters}>Clear All</button>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <h3>Product List</h3>
      <button onClick={toggleForm}>Create Product</button>
      {showForm && <ProductForm onClose={toggleForm} />}
      <table>
        <thead>
          <tr>
            <th>Product Id</th>
            <th>Name</th>
            <th>Price</th>
            <th>Stock</th>
            <th>Description</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {filteredProducts.map(product => (
            <tr key={product.productId}>
              <td>{product.productId}</td>
              <td>{product.name}</td>
              <td>{product.price}</td>
              <td>{product.stockAvailable}</td>
              <td>{product.description}</td>
              <td>
                <button onClick={() => openEditModal(product.productId)}>Edit</button>
                <button onClick={() => deleteProduct(product.productId)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {isEditModalOpen && <ProductEditForm productId={currentProductId} onClose={closeEditModal} />}
    </div>
  );
};

export default ProductList;