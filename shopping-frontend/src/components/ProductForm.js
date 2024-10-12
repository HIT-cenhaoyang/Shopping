import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './ModalForm.css';

const ProductForm = ({ onClose }) => {
  const [product, setProduct] = useState({
    name: '',
    description: '',
    price: '',
    stockAvailable: '',
    sku: '',
    category: '',
    cover_image: null,
    images: [],
    cover_image_url: '',
    images_url: '',
    uploadOption: 'file'
  });

  const [categories, setCategories] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/categories')
      .then(response => {
        setCategories(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the categories!', error);
      });
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setProduct({ ...product, [name]: value });
  };

  const handleCoverFileChange = (e) => {
    const file = e.target.files[0];
    setProduct({ ...product, cover_image: file });
  };

  const handleImagesFileChange = (e) => {
    const files = Array.from(e.target.files);
    setProduct({ ...product, images: files });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append('name', product.name);
    formData.append('description', product.description);
    formData.append('price', product.price);
    formData.append('stockAvailable', product.stockAvailable);
    formData.append('sku', product.sku);
    formData.append('category', product.category);
    formData.append('uploadOption', product.uploadOption);

    if (product.uploadOption === 'file') {
      if (product.cover_image) {
        formData.append('cover_image', product.cover_image);
      }
      for (let i = 0; i < product.images.length; i++) {
        formData.append('images', product.images[i]);
      }
    } else if (product.uploadOption === 'url') {
      formData.append('cover_image_url', product.cover_image_url);
      formData.append('images_url', product.images_url);
    }

    axios.put(`http://localhost:8080/api/products`, formData)
      .then(response => {
        onClose();
        window.location.reload();
      })
      .catch(error => {
        console.error('There was an error creating the product!', error);
        window.location.reload();
      });
  };

  return (
    <div className="modal">
      <div className="modal-content">
        <span className="close" onClick={onClose}>&times;</span>
        <form onSubmit={handleSubmit}>
          <label>Name:</label>
          <input type="text" name="name" value={product.name} onChange={handleChange} /><br />
          <label>Description:</label>
          <input type="text" name="description" value={product.description} onChange={handleChange} /><br />
          <label>Price:</label>
          <input type="text" name="price" value={product.price} onChange={handleChange} /><br />
          <label>Stock:</label>
          <input type="number" name="stockAvailable" value={product.stockAvailable} onChange={handleChange} /><br />
          <label>Sku:</label>
          <input type="text" name="sku" value={product.sku} onChange={handleChange} /><br />
          <label>Category:</label>
          <select name="category" value={product.category} onChange={handleChange}>
            <option value="">Select Category</option>
            {categories.map(category => (
              <option key={category.id} value={category.categoryName}>{category.categoryName}</option>
            ))}
          </select><br />

          <label htmlFor="uploadOption">Upload Option:</label>
          <select id="uploadOption" name="uploadOption" value={product.uploadOption} onChange={handleChange}>
            <option value="file">Upload File</option>
            <option value="url">Enter URL</option>
          </select><br />

          {product.uploadOption === 'file' && (
            <div>
              <label htmlFor="cover_image">Cover Image:</label>
              <input type="file" id="cover_image" name="cover_image" onChange={handleCoverFileChange} /><br />

              <label htmlFor="images">Product Images:</label>
              <input type="file" id="images" name="images" multiple onChange={handleImagesFileChange} /><br />
            </div>
          )}

          {product.uploadOption === 'url' && (
            <div>
              <label htmlFor="cover_image_url">Cover Image URL:</label>
              <input type="text" id="cover_image_url" name="cover_image_url" value={product.cover_image_url} onChange={handleChange} /><br />

              <label htmlFor="images_url">Product Images URLs (comma separated):</label>
              <input type="text" id="images_url" name="images_url" value={product.images_url} onChange={handleChange} /><br />
            </div>
          )}

          <button type="submit">Save</button>
        </form>
      </div>
    </div>
  );
};

export default ProductForm;