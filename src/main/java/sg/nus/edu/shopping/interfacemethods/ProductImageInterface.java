package sg.nus.edu.shopping.interfacemethods;

import sg.nus.edu.shopping.model.ProductImage;

import java.util.List;
//Author: Hannah
public interface ProductImageInterface {
    void addProductImage(int productId, ProductImage productImage);
    ProductImage getCoverImage(int productId);
    List<ProductImage> getImagesByProduct(int productId);
    ProductImage updateProductImage(int imageId, ProductImage productImage);
    void deleteProductImage(int productId, int imageId);
}
