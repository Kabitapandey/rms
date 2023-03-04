package com.kabita.rms.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Items {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int productId;
private String productName;
@Length(max = 1000)
private String productDesc;
private String productImg;
private float price;
private int stock;

@ManyToOne
@JoinColumn(name="category_id")
private Category category;

@OneToMany(mappedBy = "items",cascade = CascadeType.ALL)
private List<Cart> cart=new ArrayList<>();


@OneToMany(mappedBy = "items",cascade = CascadeType.ALL)
private List<Orders> order=new ArrayList<>();
}
