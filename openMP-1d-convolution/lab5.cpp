#include "pch.h"
#include <iostream>
#include <stdio.h>
#include <time.h>
#include <omp.h>

using namespace std;
#define N 6
#define M N/2
#define K float(N)/float(M)
#define ZERO 0




void output(float* arr, int l) {
	for (int i = 0; i < l; i++)
	{
		cout << arr[i] << " ";
	}
}

void output(float chunks[int(K)][M], int height, int width) {
	for (int i = 0; i < height; ++i)
	{
		for (int j = 0; j < width; ++j)
		{
			std::cout << chunks[i][j] << ' ';
		}
		std::cout << std::endl;
	}
}


void convolve(float* in, int input_length, float* m, int m_length, float* out)
{
#pragma omp parallel
	{
#pragma omp for
		for (int i = 0; i < input_length + m_length - 1; i++) {
			out[i] = 0.0;
			int startk = i >= input_length ? i - input_length + 1 : 0;
			int endk = i < m_length ? i : m_length - 1;
#pragma omp for
			for (int k = startk; k <= endk; k++) {
				out[i] += in[i - k] * m[k];
			}
		}
	}
}




void split_arrays(float *arr, float chunks[int(K)][M], int nArrs, int chunkSize) {

	for (int i = 0; i < nArrs; i++) {
		int start = i * chunkSize;
		int end = start + chunkSize - 1;
		if (i == nArrs - 1) {
			end = N - 1;
		}

		for (int j = start; j <= end; j++) {
			int jInd = j % chunkSize;
			chunks[i][jInd] = arr[j];
			printf("%lf ", arr[j]);
		}
		printf("\n");
	}
}

void task(float chunks[int(K)][M], int nArrs, int chunkSize,
	float Ms[int(K)][M], float outs[int(K)][M]) {
#pragma omp parallel
		{
#pragma omp sections
			{
				for (int i = 0; i < nArrs; i++)
				{
#pragma omp section 
					{
						float* arr = chunks[i];
						convolve(arr, chunkSize, Ms[i], chunkSize, outs[i]);
					}
				}
			}
		}
}

void merge_to_1d(float* b, float chunks[int(K)][M], int n, int m) {
	for (int q = 0; q < n; q++)
	{
		for (int t = 0; t < m; t++)
		{
			b[q * m + t] = chunks[q][t];
		}
	}
}

int main() {
	const int nArrs = float(N) / float(M);
	const int chunkSize = N / int(K);
	float chunks[nArrs][chunkSize];

	int n_threads = K;
	if (n_threads <= ZERO)
		n_threads = omp_get_max_threads();
	omp_set_num_threads(n_threads);
	cout << "K threads :" << n_threads << endl << endl;

	float arr[N] = { 1 ,5, 6, 7, 2 , 3 };
	float out[N];
	float m[M] = { 0,1,0 };
	float m2[M] = { 1,0,1 };

	cout << "Input array:" << endl << endl;;
	output(arr, N);


	cout << "\n\nArrays splitted:" << endl << endl;;
	split_arrays(arr, chunks, nArrs, chunkSize);

	float Ms[int(K)][M] = { { 0,1,0 } , { 1,0,1 } };
	float outs[int(K)][M];

	task(chunks, nArrs, chunkSize, Ms, outs);

	//output(outs, nArrs, chunkSize);


	float res[N];
	merge_to_1d(res, outs, nArrs, chunkSize);

	cout << "\n\nResult: " << endl << endl;;
	output(res, N);

	//////convolve(arr, N, m2, M, out);
	//////output(out, N);
	//////output(chunks, nArrs, chunkSize);

	system("pause");
}



//
////  234
////211 = 2
////
//// 234
////211 = 5
////
//// 234
//// 211 = 11
//// 
//// 234
////  211 = 10
////
//// 234
////   211 = 8
//
//
////void split_arrays(int) {
////	const int n_arrs = K;
////	const int chunk_size = M;
////	int chunks[n_arrs][chunk_size];
////	for (int i = 0; i < n_arrs; i++)
////	{
////		int j = 0;
////		while (j < chunk_size)
////		{
////			chunks[i][j] = 
////			j++;
////		}
////	}
////}