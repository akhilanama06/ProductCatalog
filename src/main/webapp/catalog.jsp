<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <title>Product Catalog</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 30px;
            background: #f4f6f9;
        }

        h1 {
            text-align: center;
            color: #243b55;
        }

        .filter {
            text-align: center;
            margin: 25px;
        }

        select, button {
            padding: 10px;
            font-size: 16px;
        }

        .products {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
        }

        .card {
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 8px #ccc;
        }

        .price {
            color: #087f5b;
            font-weight: bold;
        }
    </style>
</head>

<body>

<h1>Product Catalog</h1>

<form class="filter" action="${pageContext.request.contextPath}/catalog" method="get">
    <label for="category">Filter by Category: </label>

    <select name="category" id="category">
        <option value="All"
        ${selectedCategory == 'All' ? 'selected' : ''}>All</option>

        <option value="Electronics"
        ${selectedCategory == 'Electronics' ? 'selected' : ''}>Electronics</option>

        <option value="Clothing"
        ${selectedCategory == 'Clothing' ? 'selected' : ''}>Clothing</option>

        <option value="Books"
        ${selectedCategory == 'Books' ? 'selected' : ''}>Books</option>
    </select>

    <button type="submit">Filter</button>

</form>

<div class="products">
    <c:forEach var="p" items="${products}">
        <div class="card">
            <h3>${p.name}</h3>
            <p>Product ID: ${p.id}</p>
            <p>Category: ${p.category}</p>
            <p class="price">Price: ₹${p.price}</p>
        </div>
    </c:forEach>
</div>

<c:if test="${empty products}"> <p style="text-align:center;">No products found.</p>
</c:if>

</body>
</html>
