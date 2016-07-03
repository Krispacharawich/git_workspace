// resources /views/products.blade.php
@extends('layouts.app')

@section('content')

  <!-- Bootstrap Boilerplate -->
  <div class="panel-body">
        <!-- Display Validation Errors -->
        @include('common.errors')

        <!-- New Task Form -->
        <form action="/laravelEcom/public/product" method="POST" class="form-horizontal">
            {{ csrf_field() }}

            <!-- Task Name -->
            <div class="form-group">
                <label for="task" class="col-sm-3 control-label">Task</label>

                <div class="col-sm-6">
                    <input type="text" name="name" id="task-name" class="form-control">
                </div>
            </div>

            <!-- Add Task Button -->
            <div class="form-group">
                <div class="col-sm-offset-3 col-sm-6">
                    <button type="submit" class="btn btn-default">
                        <i class="fa fa-plus"></i> Add Task
                    </button>
                </div>
            </div>
        </form>
    </div>

    <!-- TODO: Current Tasks -->
    @if (count($products) >0)
    <div class="panel panel-default">
      <div class="panel-heading">
        Current products
      </div>

      <div class="panel-body">
        <table class="table table-striped task-table">

           //Table Headings
           <thead>
                <th>Product</th>
                <th>&nbsp;</th>
            </thead>

            //Table body

              <tbody>
                @foreach ($products as $product)
                <tr>
                    <td class="table-text">
                      <div> {{$product->name}}</div>
                    </td>

                    <td>
                      <!-- TODO: Delete Button -->
                      <form action="/laravelEcom/public/product/{{$product->id}}" method="POST">
                        {{ csrf_field() }}
                        {{ method_field('DELETE') }}
                        <button>Delete Product</button>
                    </td>
                </tr>
                @endforeach
              </tbody>
            </div>
          </div>
          @endif
@endsection
