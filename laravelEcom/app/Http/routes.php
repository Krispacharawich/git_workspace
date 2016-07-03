<?php
use App\Product;
use Illuminate\Http\Request;
/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the controller to call when that URI is requested.
|
*/

Route::get('/', function () {
  $products = Product::orderBy('created_at','asc')->get();

    return view('products',['products'=>$products]);
});
/*
 Add a new product
*/
Route::post('/product',function (Request $request){
    $validator = Validator::make($request->all(),['name'=>'required|max:255',]);
    if($validator->fails()){
      return redirect('/')->withInput()->withErrors($validator);
    }

    $product = new Product;
    $product->name = $request->name;
    $product->save();
    return redirect('/');
});

Route::get('/product',function (Request $request) {
  return view('products');
});

/*
 Delete an existing product
*/
Route::delete('/product/{id}',function ($id){
  Product::findOrFail($id)->delete();
  return redirect('/');
});
