from flask import Flask, render_template, request, redirect
import threading

app = Flask(__name__)

database_file = 'BD1.txt'
lock = threading.Lock()

def read_data():
    try:
        with open(database_file, 'r') as file:
            lines = file.readlines()
            data = [line.split(',') for line in lines]
        return data
    except FileNotFoundError:
        return []

def update_data(data):
    with open(database_file, 'a') as file:
        file.write(''.join(data)+'')

def write_data(data):
    with open(database_file, 'w') as file:
        file.write(''.join(data)+'')

@app.route('/')
def index():
    data = read_data()
    return render_template('index.html', data=data)

@app.route('/delete/<int:index>')
def delete(index):
    data = read_data()
    write_data('')
    if 0 <= index < len(data):
        del(data[index])
    for i in range(len(data)):
        record = data[i][0] + ',' + data[i][1] + ',' + data[i][2] + ',' + data[i][3] + ',' + data[i][4] + ',' + data[i][5]
        update_data(record)
    return redirect('/')

@app.route('/add', methods=['POST'])
def add():
    try:
        if request.method == 'POST':
            id_producto = request.form['id_producto']
            nombre_producto = request.form['nombre_producto']
            unidad = request.form['unidad']
            descripcion = request.form['descripcion']
            cantidad = request.form['cantidad']
            precio = request.form['precio']

            # Imprimir el contenido de la solicitud POST
            print(f"Nuevo producto: {id_producto}, {nombre_producto}, {unidad}, {descripcion}, {cantidad}, {precio}")

            update_data(id_producto + ',' + nombre_producto + ',' + descripcion + ',' + unidad + ',' + cantidad + ',' + precio)
        return redirect('/')
    except Exception as e:
        print(f"Error en la ruta /add: {str(e)}")
        return "Error interno en el servidor", 500
@app.route('/consulta_existencia/<int:id_producto>')
def consultar_existencia(id_producto):
    data=read_data()
    print(id_producto)
    for product in data:
        if int(product[0]) == id_producto:
            return ({"id_producto": id_producto, "existencias": int(product[4])})
    
    return ({"id_producto": id_producto, "existencias": 0})
if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8888)
