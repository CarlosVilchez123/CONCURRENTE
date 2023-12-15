from flask import Flask, render_template, request, redirect

app = Flask(__name__)

database_file = 'DB_ventas.txt'
database_detalles = 'DB_Detalles.txt'

def sync_data_with_replicas(data):
    replicas_ips = ['192.168.1.101', '192.168.1.102']

    for ip in replicas_ips:
        url = f'http://{ip}:8888/sync_data'
        payload = {'data': data}

        try:
            response = request.post(url, data=payload)
            if response.json()['success']:
                print(f'Data synchronized with replica at {ip}')
            else:
                print(f'Error syncing data with replica at {ip}: {response.json()["error"]}')
        except Exception as e:
            print(f'Error connecting to replica at {ip}: {str(e)}')

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
    
@app.route('/realizar_venta', methods=['POST'])
def realizar_ventas():
    print("entro a la funcion")
    return "hola mundo"

@app.route('/sync_data', methods=['POST'])
def sync_data():
    try:
        if request.method == 'POST':
            print("accedi a esto")

        return "hola mundo"
    except Exception as e:
        return "hola mundo"


if __name__ == '__main__':
    app.run(host='0.0.0.0',port=8000)