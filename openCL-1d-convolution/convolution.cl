__kernel
void convolution(__global float * A, __global float * B, __global float * C, int a_size, int b_size) {
	int row = get_global_id(0);

	if (row >= a_size)
		return;

	float result = 0.0;
	for (int i = 0; i < b_size; ++i) {
		int x = row + i - b_size;
		if (x >= 0 && x < a_size)
			result += A[x] * B[i];
	}

	C[row] = result;

	//float result = 0.0;
	//	
	//	int startk = row >= b_size ? row - a_size + 1 : 0;
	//	int endk = row < b_size ? row : b_size - 1;
	//	for (int k = startk; k <= endk; k++) {
	//		C[row] += A[row - k] * B[k];
	//	}

 //  C[row] = result;
}


//
//__kernel void convolution(__global float* Ain, __global  float* m, __global  float* Cout,
//	int input_length, int m_length) {
//
//	  // int row = get_global_id(0);
//	  // int col = get_global_id(1);
//
//	  // if (row >= input_length || col >= m_length)
//			//return;
//
//		for (int i = 0; i < input_length + m_length - 1; i++) {
//			Cout[i] = 0.0;
//			int startk = i >= input_length ? i - input_length + 1 : 0;
//			int endk = i < m_length ? i : m_length - 1;
//			for (int k = startk; k <= endk; k++) {
//				Cout[i] += Ain[i - k] * m[k];
//			}
//		}
//}



//
//
//__kernel
//void convolution(__global float * A, __global float * B, __global float * C, int a_size, int b_size) {
//	int row = get_global_id(0);
//	int col = get_global_id(1);
//
//	if (row >= a_size || col >= a_size)
//		return;
//
//	float result = 0.0;
//	for (int i = 0; i < b_size; ++i) {
//		for (int j = 0; j < b_size; ++j) {
//			int x = row + i - b_size / 2;
//			int y = col + j - b_size / 2;
//			if (x >= 0 && x < a_size && y >= 0 && y < a_size)
//				result += A[x * a_size + y] * B[i * b_size + j];
//		}
//	}
//
//	C[row * a_size + col] = result;
//}







//working 1d
//__kernel
//void convolution(__global float * A, __global float * B, __global float * C, int a_size, int b_size) {
//	int row = get_global_id(0);
//
//	if (row >= a_size)
//		return;
//
//	float result = 0.0;
//	for (int i = 0; i < b_size; ++i) {
//		int x = row + i - b_size;
//		if (x >= 0 && x < a_size)
//			result += A[x] * B[i];
//	}
//
//	C[row] = result;
//
//	//float result = 0.0;
//	//	
//	//	int startk = row >= b_size ? row - a_size + 1 : 0;
//	//	int endk = row < b_size ? row : b_size - 1;
//	//	for (int k = startk; k <= endk; k++) {
//	//		C[row] += A[row - k] * B[k];
//	//	}
//
// //  C[row] = result;
//}