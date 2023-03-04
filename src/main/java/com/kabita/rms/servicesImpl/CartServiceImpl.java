package com.kabita.rms.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kabita.rms.entities.Cart;
import com.kabita.rms.entities.Items;
import com.kabita.rms.entities.UserModel;
import com.kabita.rms.exception.ResourceNotFoundException;
import com.kabita.rms.payload.CartDto;
import com.kabita.rms.repository.CartRepository;
import com.kabita.rms.repository.ItemsRepository;
import com.kabita.rms.repository.UserRepository;
import com.kabita.rms.services.CartServices;

@Service
public class CartServiceImpl implements CartServices {
	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ItemsRepository itemRepo;

	@Override
	public CartDto addToCart(CartDto cartDto, Integer userId, Integer productId) {
//		getting object type of cartDto from user and converting it into Cart type
		Cart cart = this.modelMapper.map(cartDto, Cart.class);
//		getting cart item 
		Cart getCartItem = this.cartRepo.getCartByUserAndProduct(userId, productId);
//		getting user object
		UserModel user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
//		getting the items object 
		Items item = this.itemRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));
//		setting user and items to cart object
		cart.setUser(user);
		cart.setItems(item);
//if cart does not contain that product than adding the product else increasing the quantity
		if (getCartItem != null) {
			int quantity = getCartItem.getQuantity();
			getCartItem.setQuantity(quantity + 1);
			this.cartRepo.save(getCartItem);

			return this.modelMapper.map(getCartItem, CartDto.class);

		}

		Cart addedCart = this.cartRepo.save(cart);

		return this.modelMapper.map(addedCart, CartDto.class);
	}

	@Override
	public List<CartDto> getAllCart(Integer userId) {
//		getting user object
		UserModel user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
//retrieving cart items as the user 
		List<Cart> cart = this.cartRepo.getCartByUser(user);

		return cart.stream().map((c) -> this.modelMapper.map(c, CartDto.class)).collect(Collectors.toList());
	}

	@Override
	public CartDto updateCart(CartDto cartDto, Integer cartId) {
//		getting cart object
		Cart cart = this.cartRepo.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart", "Cart Id", cartId));

		cart.setQuantity(cartDto.getQuantity());
		
//saving the updated cart object to database
		Cart updatedCart = this.cartRepo.save(cart);

		return this.modelMapper.map(updatedCart, CartDto.class);
	}

	@Override
	public CartDto getSingleCart(Integer cartId) {
//		getting single cart object
		Cart cart = this.cartRepo.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart", "Cart Id", cartId));

		return this.modelMapper.map(cart, CartDto.class);
	}

	@Override
	public void deleteCart(Integer cartId) {
//	getting cart object
		Cart cart = this.cartRepo.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart", "Cart Id", cartId));
//		jpa method for deleting item
		this.cartRepo.delete(cart);
	}
}
