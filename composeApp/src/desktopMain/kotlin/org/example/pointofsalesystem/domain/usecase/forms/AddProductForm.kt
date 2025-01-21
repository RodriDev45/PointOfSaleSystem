package org.example.pointofsalesystem.domain.usecase.forms

import org.example.pointofsalesystem.domain.model.AddProductModel
import org.example.pointofsalesystem.domain.model.FormField
import org.example.pointofsalesystem.domain.model.LoginModel
import java.io.File

class AddProductForm {
    private val _image: FormField<File?> = FormField(
        initialValue = null,
        validator = { value ->
            null
        }
    )
    val image = _image.state

    private val _code: FormField<String> = FormField(
        initialValue = "",
        validator = { value ->
            val regex = Regex("^[1-9][0-9]{0,12}$")
            if(value.isEmpty()){
                "Código requerido"
            } else if(!regex.matches(value)){
                "El codigo es solo numerico, maximo de 13 números"
            } else null
        }
    )
    val code = _code.state

    private val _name: FormField<String> = FormField(
        initialValue = "",
        validator = { value ->
            if(value.isBlank()){
                "Name is required"
            } else null
        }
    )
    val name = _name.state

    private val _category: FormField<Long?> = FormField(
        initialValue = null,
        validator = { value ->
            if(value == null){
                "Category is required"
            } else null
        }
    )
    val category = _category.state

    private val _brand: FormField<String> = FormField(
        initialValue = "",
        validator = { value ->
            if(value.isBlank()){
                "Brand is required"
            } else null
        }
    )
    val brand = _brand.state

    private val _quantity: FormField<Int> = FormField(
        initialValue = 0,
        validator = { value ->
            if(value <= 0){
                "The amount must be greater than 0"
            } else null
        }
    )
    val quantity = _quantity.state

    private val _minimumStock: FormField<Int> = FormField(
        initialValue = 0,
        validator = { value ->
            if(value <= 0){
                "The minimum stock must be greater than 0"
            }else if(value >= _maximumStock.value){
                "The minimum stock must be less than the maximum"
            }
            else null
        }
    )
    val minimumStock = _minimumStock.state

    private val _maximumStock: FormField<Int> = FormField(
        initialValue = 0,
        validator = { value ->
            if(value <= 0){
                "The maximum stock must be greater than 0"
            } else if(value <= _minimumStock.value){
                "The maximum stock must be greater than the minimum"
            } else null
        }
    )
    val maximumStock = _maximumStock.state

    private val _purchasePrice: FormField<Double> = FormField(
        initialValue = 0.0,
        validator = { value ->
            if(value <= 0){
                "Purchase price is required"
            } else null
        }
    )
    val purchasePrice = _purchasePrice.state

    private val _salePrice: FormField<Double> = FormField(
        initialValue = 0.0,
        validator = { value ->
            if(value <= 0){
                "Sale price is required"
            } else null
        }
    )
    val salePrice = _salePrice.state

    fun setImage(value: File){
        _image.update(value)
    }

    fun setCode(value: String){
        _code.update(value)
    }

    fun setName(value: String){
        _name.update(value)
    }

    fun setCategory(value: Long){
        _category.update(value)
    }

    fun setBrand(value: String){
        _brand.update(value)
    }

    fun setQuantity(value: Int){
        _quantity.update(value)
    }

    fun setMinimumStock(value: Int){
        _minimumStock.update(value)
    }

    fun setMaximumStock(value: Int){
        _maximumStock.update(value)
        _minimumStock.validate()
    }

    fun setPurchasePrice(value: Double){
        _purchasePrice.update(value)
    }

    fun setSalePrice(value: Double){
        _salePrice.update(value)
    }

    fun validate(onSubmit: (data: AddProductModel) -> Unit) {
        var isValid = true
        listOf(
            _image,
            _code,
            _name,
            _category,
            _brand,
            _quantity,
            _minimumStock,
            _maximumStock,
            _purchasePrice,
            _salePrice,
        ).forEach { field ->
            field.validate()
            if(field.state.value.error != null) isValid = false
        }
        if(isValid) {
            onSubmit(
                AddProductModel(
                    image = _image.value,
                    code = _code.value.toLong(),
                    name = _name.value,
                    category = _category.value!!,
                    brand = _brand.value,
                    quantity = _quantity.value,
                    minStock = _minimumStock.value,
                    maxStock = _maximumStock.value,
                    purchasePrice = _purchasePrice.value,
                    salePrice = _salePrice.value
                )
            )

        }
    }

    fun resetFields(){
        listOf(
            _image,
            _code,
            _name,
            _category,
            _brand,
            _quantity,
            _minimumStock,
            _maximumStock,
            _purchasePrice,
            _salePrice,
        ).forEach { field -> field.reset()}
    }


}