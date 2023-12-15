from flask import Flask, render_template, request, redirect

app = Flask(__name__)

database_file = 'DB_ventas.txt'
database_detalles = 'DB_Detalles.txt'

def read_data(database):
    try:
        with open(database, 'r') as file:
            lines = file.readlines()
            data = [line.split(',') for line in lines]
        return data
    except FileNotFoundError:
        return []
    
def update_data(data, database):
    with open(database, 'a') as file:
        file.write(''.join(data)+'')

def wirte_data(data, database):
    with open(database, 'w') as file:
        file.write(''.join(data)+'')

@app.route('/')
def index():
    data = read_data(database_file)
    return render_template('index.html', data = data)

@app.route('/delete/<int:index>')
def delete(index):
    data = read_data(database_file)
    wirte_data('', database_file)
    if 0 <=index <len(data):
        del(data[index])
    for i in range(len(data)):
        record = data[i][0]+','+data[i][1]+','+data[i][2]+','+data[i][3]
        print(record)
        update_data(record,database_file)
    return redirect('/')

@app.route('/add', methods=['POST'])
def add():
    try:
        if request.method == 'POST':
            id_venta = request.form['id_venta']
            RUC = request.form['RUC']
            Nombre = request.form['nombre']
            Costo = request.form['costo']
            update_data(id_venta+','+RUC+','+Nombre+','+Costo, database_file)
        return redirect('/')
    except Exception as e:
        print(f"Error en la ruta /add: {str(e)}")
        return "Error interno en el servidor", 500
    
@app.route('/detalles', methods=['POST'])
def detalles():
    if request.method == 'POST':
        data = read_data(database_detalles)
        return render_template('detalles.html', data = data)

if __name__ == '__main__':
    app.run(host='0.0.0.0',port=8888)