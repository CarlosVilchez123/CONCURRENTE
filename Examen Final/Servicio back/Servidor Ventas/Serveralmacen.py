from flask import Flask, render_template, request, redirect

app = Flask(__name__)

database_file = 'DB_ventas.txt'

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
def wirte_data(data):
    with open(database_file, 'w') as file:
        file.write(''.join(data)+'')
@app.route('/')
def index():
    data = read_data()
    return render_template('index.html', data = data)

@app.route('/delete/<int:index>')
def delete(index):
    data = read_data()
    wirte_data('')
    if 0 <=index <len(data):
        del(data[index])
    for i in range(len(data)):
        record = data[i][0]+','+data[i][1]+','+data[i][2]+','+data[i][3]+','+data[i][4]+','+data[i][5]
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

            update_data(id_producto+','+nombre_producto+','+descripcion+','+unidad+','+cantidad+','+precio)
        return redirect('/')
    except Exception as e:
        print(f"Error en la ruta /add: {str(e)}")
        return "Error interno en el servidor", 500

if __name__ == '__main__':
    app.run(host='0.0.0.0',port=8888)