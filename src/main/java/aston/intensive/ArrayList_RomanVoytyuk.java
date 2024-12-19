package aston.intensive;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * реализация динамически увеличивающегося массива.
 * реализует методы интерфейс IntensiveList:
 *  size(),
 *  add(),
 *  add(index, element),
 *  get(index),
 *  set(index, element),
 *  remove(index),
 *  clear(),
 *  quickSort(comparator),
 *  isSorted(),
 *  split(size)
 *
 * @param <E>
 */

public class ArrayList_RomanVoytyuk<E> implements IntensiveList<E> {

    private final int INITIAL_CAPACITY = 10;
    private Object[] list;
    private int size = 0;
    private boolean sorted = false;

    /**
     * Конструктор без параметров. Инициализирует массив
     */
    public ArrayList_RomanVoytyuk() {
        list = new Object[INITIAL_CAPACITY];
    }


    /**
     * Метод возвращает значение приватного поля int size - количество элементов в массиве
     * Количество элементов может быть меньше либо равно длине массива.
     * @return int - количество элементов в массиве
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Метод добавляет элемент в ячейку массива индексом size + 1.
     *
     * @param element элемент типа E
     */
    @Override
    public void add(E element) {
        //если массив заполнен, то увеличивает массив
        if (size == list.length) {
            grow();
        }
        list[size++] = element;
        sorted = false;
    }

    /**
     * Добавляет элемент в ячейку массива с конкретным индексом
     * @param index int в диапазоне от 0(включительно) до size
     * в случае нарушения диапазона выбрасывается исключение IndexOutOfBounds
     * @param element - тип элемента E
     */
    @Override
    public void add(int index, E element) {
        Objects.checkIndex(index, size);
        if (size == list.length) {
            grow();
        }

        Object[] right = Arrays.copyOfRange(list, index, list.length);
        list[index] = element;
        for (int i = index + 1, j = 0; i < right.length; i++, j++) {
            Object temp = right[j];
            if(temp == null) break;
            list[i] = right[j];
        }
        size++;
        sorted = false;
    }

    /**
     * Возвращает E e из массива
     * @param index int в диапазоне от 0(включительно) до size
     *      * в случае нарушения диапазона выбрасывается исключение IndexOutOfBounds
     * @return возвращает элемент хранящийся в ячейке массива.
     */
    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        Objects.checkIndex(index, size);
        return (E) list[index];
    }

    /**
     * Заменяет старое значение на новое в конкретной ячейке массива
     * @param index int в диапазоне от 0(включительно) до size
     * @param element тип элемента E
     * @return возвращает объект типа Е, который был заменен новым значением
     */
    @Override
    public E set(int index, E element) {
        Objects.checkIndex(index, size);
        E old = get(index);
        list[index] = element;
        sorted = false;
        return old;
    }

    /**
     * Удаляет значение из конкретной ячейки массива по индексу.
     * После удаления происходит смещение массива с заполнением освободившейся ячейки.
     * @param index индекс удаляемого элемента в массиве. Диапазон допустимых индексов от 0 (включительно) до size (исключая)
     * @return Е возвращает удаленный элемент
     */
    @Override
    public E remove(int index) {
        Objects.checkIndex(index, size);
        E removed = get(index);
        System.arraycopy(list, index + 1, list, index, size - index - 1);
        list[--size] = null;
        sorted = false;
        return removed;
    }

    /**
     * Удаляет данные. Размер (size) 0;
     */
    @Override
    public void clear() {
        list = new Object[INITIAL_CAPACITY];
        size = 0;
        sorted = false;
    }

    /**
     * Сортирует данные, использую быструю сортировки.
     * @param comparator параметр типа Comparator<E>
     */
    @Override
    public void quickSort(Comparator<E> comparator) {
        if (sorted) {
            return;
        }
        System.arraycopy(list, 0, list, 0, size);
        sort(0, size - 1, comparator);
        sorted = true;
    }

    /**
     * Массив проверяет состояние массива. Если ранее массив сортировали с помощью метода quickSort(),
     * то возвращает true, в противном случае false.
     * @return boolean. True - если массив отсортирован, в противном случае - false
     */

    @Override
    public boolean isSorted() {
        return sorted;
    }

    /**
     *  Метод сокращает массив до указанного размера.
     * @param size - размер массива, после сокращения
     */
    @Override
    public void split(int size) {
        if(size > this.size) return;
        System.arraycopy(list, 0, list, 0, size);
        this.size = size;
    }

    private void grow() {
        int newCapacity = list.length * 2;
        list = Arrays.copyOf(list, newCapacity);
    }

    private void sort(int low, int high, Comparator<E> comparator ) {
        //базовые случаи
        if(list.length == 0) {
            return;
        }
        if (low >= high) {
            return;
        }

        //устанавливаем точку опоры равное элементу в середине массива
        int middle = low + (high - low) / 2;
        E pivot = get(middle);
        //устанавливаем индексы
        int i = low;  //индекс элементов начала массива
        int j = high;  //индекс элементов конца массива
        //Слева от точки опоры собираются элементы меньше точки опоры. С право - больше
        //проходим по массиву слева и справа от точки опоры.
        while (i < j) {
            //цель двух нижних блоков while найти индексы элементов слева от точки опоры, которые больше точки опоры
            //и элементов справа от точки опоры со значением меньше точки опоры
            //индекс увеличивается пока элементы меньше точки опоры
            while (comparator.compare(get(i), pivot) < 0) {
                i++;
            }
            //индекс уменьшается пока элементы больше точки опоры
            while (comparator.compare(get(j), pivot) > 0) {
                j--;
            }
            //Меняем местами выявленные элементы. Элемент значение которого больше точки опоры перемещается в конец
            // массива, элемент с меньшим значением в начало
            if (i <= j) {
                E temp = get(i);
                set(i, get(j));
                set(j, temp);
                i++;
                j--;
            }
        }

        //вызов рекурсии для сортировки левой и правой части.
        if (low < j) {
            sort(low, j, comparator);
        }
        if (high > i) {
            sort(i, high, comparator);
        }

    }
}
